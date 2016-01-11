/*
 * Copyright 2000-2015 JetBrains s.r.o.
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
package com.intellij.vcs.log;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class CommitId {
  @NotNull private final Hash myHash;
  @NotNull private final VirtualFile myRoot;

  public CommitId(@NotNull Hash hash, @NotNull VirtualFile root) {
    myHash = hash;
    myRoot = root;
  }

  @NotNull
  public Hash getHash() {
    return myHash;
  }

  @NotNull
  public VirtualFile getRoot() {
    return myRoot;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CommitId commitId = (CommitId)o;

    if (!myHash.equals(commitId.myHash)) return false;
    if (!myRoot.equals(commitId.myRoot)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = myHash.hashCode();
    result = 31 * result + myRoot.hashCode();
    return result;
  }
}
