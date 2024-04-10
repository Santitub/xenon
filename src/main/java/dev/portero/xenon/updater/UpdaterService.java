package dev.portero.xenon.updater;

import dev.portero.xenon.injector.annotations.component.Service;

@Service
class UpdaterService {

//    private static final GitRepository GIT_REPOSITORY = GitRepository.of("joshuaportero", "xenon");
//
//    private final GitCheck gitCheck = new GitCheck();
//    private final Lazy<GitCheckResult> gitCheckResult;
//
//    @Inject
//    UpdaterService(PluginDescriptionFile pluginDescriptionFile) {
//        this.gitCheckResult = new Lazy<>(() -> {
//            String version = pluginDescriptionFile.getVersion();
//
//            return this.gitCheck.checkRelease(GIT_REPOSITORY, GitTag.of("v" + version));
//        });
//    }
//
//    CompletableFuture<Boolean> isUpToDate() {
//        return CompletableFuture.supplyAsync(() -> {
//            GitCheckResult checkResult = this.gitCheckResult.get();
//
//            return checkResult.isUpToDate();
//        });
//    }
}
