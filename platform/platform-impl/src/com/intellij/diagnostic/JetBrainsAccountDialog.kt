/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.diagnostic

import com.intellij.CommonBundle
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.ide.BrowserUtil
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.dialog
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.layout.*
import com.intellij.ui.layout.CCFlags.*
import com.intellij.ui.layout.LCFlags.*
import com.intellij.util.io.encodeUrlQueryParameter
import java.awt.Component
import javax.swing.JPasswordField
import javax.swing.JTextField

@JvmOverloads
fun showJetBrainsAccountDialog(parent: Component, project: Project? = null): DialogBuilder {
  val credentials = ErrorReportConfigurable.getCredentials()
  val userField = JTextField(credentials?.userName)
  val passwordField = JPasswordField(credentials?.password?.toString())

  // if no user name - never stored and so, defaults to remember. if user name set, but no password, so, previously was stored without password
  val rememberCheckBox = JBCheckBox(CommonBundle.message("checkbox.remember.password"), credentials?.userName == null || !credentials?.password.isNullOrEmpty())

  val panel = panel(fillX) {
    label("Login to JetBrains Account to get notified when the submitted\nexceptions are fixed.", span, wrap)
    label("Username:")
    userField(grow, wrap)

    label("Password:")
    passwordField(grow, wrap)
    rememberCheckBox(skip, split, grow)
    link("Forgot password?", wrap, right) {
      BrowserUtil.browse("https://account.jetbrains.com/forgot-password?username=${userField.text.trim().encodeUrlQueryParameter()}")
    }

    note("""Do not have an account? <a href="https://account.jetbrains.com/login">Sign Up</a>""", span, wrap)
  }

  return dialog(
      title = DiagnosticBundle.message("error.report.title"),
      centerPanel = panel,
      preferedFocusComponent = if (credentials?.userName == null) userField else passwordField,
      project = project,
      parent = if (parent.isShowing) parent else null) {
    val userName = userField.text
    if (!userName.isNullOrBlank()) {
      PasswordSafe.getInstance().set(CredentialAttributes(ErrorReportConfigurable.SERVICE_NAME, userName), Credentials(userName, if (rememberCheckBox.isSelected) passwordField.password else null))
    }
  }
}