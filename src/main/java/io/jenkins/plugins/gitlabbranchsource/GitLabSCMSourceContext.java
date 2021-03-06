package io.jenkins.plugins.gitlabbranchsource;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.model.TaskListener;
import java.util.EnumSet;
import java.util.Set;
import jenkins.scm.api.SCMHeadObserver;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.SCMSourceCriteria;
import jenkins.scm.api.mixin.ChangeRequestCheckoutStrategy;
import jenkins.scm.api.trait.SCMSourceContext;

public class GitLabSCMSourceContext
        extends SCMSourceContext<GitLabSCMSourceContext, GitLabSCMSourceRequest> {

    private boolean wantBranches;
    private boolean wantTags;
    private boolean wantOriginMRs;
    private boolean wantForkMRs;

    @NonNull
    private Set<ChangeRequestCheckoutStrategy> originMRStrategies = EnumSet.noneOf(ChangeRequestCheckoutStrategy.class);
    @NonNull
    private Set<ChangeRequestCheckoutStrategy> forkMRStrategies = EnumSet.noneOf(ChangeRequestCheckoutStrategy.class);
    @NonNull
    private WebhookRegistration webhookRegistration = WebhookRegistration.SYSTEM;
    private boolean notificationsDisabled;

    public GitLabSCMSourceContext(@CheckForNull SCMSourceCriteria criteria, @NonNull SCMHeadObserver observer) {
        super(criteria, observer);
    }

    public final boolean wantBranches() {
        return wantBranches;
    }

    public final boolean wantTags() {
        return wantTags;
    }

    public final boolean wantMRs() {
        return wantOriginMRs || wantForkMRs;
    }

    public final boolean wantOriginMRs() {
        return wantOriginMRs;
    }

    public final boolean wantForkMRs() {
        return wantForkMRs;
    }

    @NonNull
    public final Set<ChangeRequestCheckoutStrategy> originMRStrategies() {
        return originMRStrategies;
    }

    @NonNull
    public final Set<ChangeRequestCheckoutStrategy> forkMRStrategies() {
        return forkMRStrategies;
    }

    @NonNull
    public final WebhookRegistration webhookRegistration() {
        return webhookRegistration;
    }

    public final boolean notificationsDisabled() {
        return notificationsDisabled;
    }

    @NonNull
    public GitLabSCMSourceContext wantBranches(boolean include) {
        wantBranches = wantBranches || include;
        return this;
    }

    @NonNull
    public GitLabSCMSourceContext wantTags(boolean include) {
        wantTags = wantTags || include;
        return this;
    }

    @NonNull
    public GitLabSCMSourceContext wantOriginMRs(boolean include) {
        wantOriginMRs = wantOriginMRs || include;
        return this;
    }

    @NonNull
    public GitLabSCMSourceContext wantForkMRs(boolean include) {
        wantForkMRs = wantForkMRs || include;
        return this;
    }

    @NonNull
    public GitLabSCMSourceContext withOriginMRStrategies(Set<ChangeRequestCheckoutStrategy> strategies) {
        originMRStrategies.addAll(strategies);
        return this;
    }

    @NonNull
    public GitLabSCMSourceContext withForkMRStrategies(Set<ChangeRequestCheckoutStrategy> strategies) {
        forkMRStrategies.addAll(strategies);
        return this;
    }

    @NonNull
    public final GitLabSCMSourceContext webhookRegistration(WebhookRegistration mode) {
        webhookRegistration = mode;
        return this;
    }

    @NonNull
    public final GitLabSCMSourceContext withNotificationsDisabled(boolean disabled) {
        this.notificationsDisabled = disabled;
        return this;
    }

    @NonNull
    @Override
    public GitLabSCMSourceRequest newRequest(@NonNull SCMSource source, @CheckForNull TaskListener listener) {
        return new GitLabSCMSourceRequest(source, this, listener);
    }
}
