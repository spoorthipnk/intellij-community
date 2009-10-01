package git4idea.update;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import git4idea.config.GitVcsSettings;
import git4idea.i18n.GitBundle;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

/**
 * Configurable for the update session
 */
public class GitUpdateConfigurable implements Configurable {
  /** The vcs settings for the configurable */
  private final GitVcsSettings mySettings;
  /** The options panel */
  private GitUpdateOptionsPanel myPanel;

  /**
   * The constructor
   * @param settings the settings object
   */
  public GitUpdateConfigurable(GitVcsSettings settings) {
    mySettings = settings;
  }

  /**
   * {@inheritDoc}
   */
  @Nls
  public String getDisplayName() {
    return GitBundle.getString("update.options.display.name");
  }

  /**
   * {@inheritDoc}
   */
  public Icon getIcon() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public String getHelpTopic() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public JComponent createComponent() {
    myPanel = new GitUpdateOptionsPanel();
    return myPanel.getPanel();  //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * {@inheritDoc}
   */
  public boolean isModified() {
    return myPanel.isModified(mySettings);
  }

  /**
   * {@inheritDoc}
   */
  public void apply() throws ConfigurationException {
    myPanel.applyTo(mySettings);
  }

  /**
   * {@inheritDoc}
   */
  public void reset() {
    myPanel.updateFrom(mySettings);
  }

  /**
   * {@inheritDoc}
   */
  public void disposeUIResources() {
    myPanel = null;
  }
}
