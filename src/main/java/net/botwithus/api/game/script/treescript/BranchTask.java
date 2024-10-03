package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.script.treescript.permissive.Permissive;
import net.botwithus.api.game.script.treescript.permissive.Result;
import net.botwithus.rs3.script.Script;

import java.util.concurrent.Callable;
import lombok.SneakyThrows;

/**
 * Represents a branch task in a tree. This task is not a leaf node and
 * must provide success and failure tasks based on validation.
 */
public class BranchTask extends TreeTask {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private Permissive[][] permissives = new Permissive[][]{new Permissive[0]};
    private Callable<Permissive[][]> permissivesC;
    private TreeTask successTask, failureTask;
    private Callable<TreeTask> successTaskC, failureTaskC;

    public BranchTask(Script script, String desc) {
        super(script, desc);
    }
    public BranchTask(Script script, String desc, String definedIn) {
        super(script, desc, definedIn);
    }

    public BranchTask(Script script, String desc, Callable<TreeTask> successTask, TreeTask failureTask, Permissive[]... permissives) {
        super(script, desc);
        this.permissives = permissives;
        this.successTaskC = successTask;
        this.failureTask = failureTask;
    }
    public BranchTask(Script script, String desc, TreeTask successTask, Callable<TreeTask> failureTask, Permissive[]... permissives) {
        super(script, desc);
        this.permissives = permissives;
        this.successTask = successTask;
        this.failureTaskC = failureTask;
    }
    public BranchTask(Script script, String desc, Callable<TreeTask> successTask, Callable<TreeTask> failureTask, Permissive[]... permissives) {
        super(script, desc);
        this.permissives = permissives;
        this.successTaskC = successTask;
        this.failureTaskC = failureTask;
    }
    public BranchTask(Script script, String desc, TreeTask successTask, TreeTask failureTask, Permissive[]... permissives) {
        super(script, desc);
        this.permissives = permissives;
        this.successTask = successTask;
        this.failureTask = failureTask;
    }
    public BranchTask(Script script, String desc, TreeTask successTask, TreeTask failureTask, Callable<Permissive[][]> permissives) {
        super(script, desc);
        this.permissivesC = permissives;
        this.successTask = successTask;
        this.failureTask = failureTask;
    }
    public BranchTask(Script script, String desc, String definedIn, Callable<TreeTask> successTask, TreeTask failureTask, Permissive[]... permissives) {
        super(script, desc, definedIn);
        this.permissives = permissives;
        this.successTaskC = successTask;
        this.failureTask = failureTask;
    }
    public BranchTask(Script script, String desc, String definedIn, TreeTask successTask, Callable<TreeTask> failureTask, Permissive[]... permissives) {
        super(script, desc, definedIn);
        this.permissives = permissives;
        this.successTask = successTask;
        this.failureTaskC = failureTask;
    }
    public BranchTask(Script script, String desc, String definedIn, Callable<TreeTask> successTask, Callable<TreeTask> failureTask, Permissive[]... permissives) {
        super(script, desc, definedIn);
        this.permissives = permissives;
        this.successTaskC = successTask;
        this.failureTaskC = failureTask;
    }
    public BranchTask(Script script, String desc, String definedIn, TreeTask successTask, TreeTask failureTask, Permissive[]... permissives) {
        super(script, desc, definedIn);
        this.permissives = permissives;
        this.successTask = successTask;
        this.failureTask = failureTask;
    }
    public BranchTask(Script script, String desc, String definedIn, TreeTask successTask, TreeTask failureTask, Callable<Permissive[][]> permissives) {
        super(script, desc, definedIn);
        this.permissivesC = permissives;
        this.successTask = successTask;
        this.failureTask = failureTask;
    }

    /** {@inheritDoc}
     * Not Executed in Branch Tasks*/
    @Override
    public void execute() {
    }

    /** {@inheritDoc} */
    @Override
    public TreeTask successTask(){
        if (successTaskC != null) {
            try {
                successTask = successTaskC.call();
            } catch (Exception e){
                log.atSevere().withCause(e).log("Failed to determine the result of the Callable<successTaskC>");
            }
        }
        return successTask;
    }

    /** {@inheritDoc} */
    @Override
    @SneakyThrows
    public boolean validate() {
        var groupIsValid = false;
        Permissive curPerm = null;

        try {
            if (getPermissives().length == 0) {
                return false; // No permissives to validate, so the branch is invalid
            }

            for (var group : getPermissives()) {
                groupIsValid = true; // Assume the group is valid initially
                for (var perm : group) {
                    curPerm = perm;
                    if (!perm.isMet()) {
                        groupIsValid = false; // Set to false if any permissive in the group is not met
                        break; // No need to check further in this group
                    }
                }
                if (groupIsValid) {
                    break; // A valid group is found, no need to check further groups
                }
            }
        } catch (Exception e) {
            groupIsValid = false; // Ensure validation fails in case of an exception
            log.atSevere().withCause(e).log("Could not process permissive: " + (curPerm != null ? curPerm.getName() : "null"));
        }

        latestValidate = new Result(groupIsValid);
        return groupIsValid;
    }

    /** {@inheritDoc} */
    @Override
    public TreeTask failureTask() {
        if (failureTaskC != null) {
            try {
                failureTask = failureTaskC.call();
            } catch (Exception e){
                log.atSevere().withCause(e).log("Failed to determine the result of the Callable<failureTaskC>");
            }
        }
        return failureTask;
    }

    /** {@inheritDoc}
     * Will always be False for BranchTasks */
    @Override
    public final boolean isLeaf() {
        return false;
    }

    public Permissive[][] getPermissives() {
        if (permissivesC != null) {
            try {
                permissives = permissivesC.call();
            } catch (Exception e){
                log.atSevere().withCause(e).log("Failed to determine the result of the Callable<permissivesC>");
            }
        }
        return permissives;
    }

    public void setPermissives(Permissive[]... permissives) {
        this.permissives = permissives;
    }

    public void updateResultExpirationTime(int millis) {
        updateResultExpirationTime(this, millis);
    }

    public static void updateResultExpirationTime(BranchTask task, int millis) {
        if (task == null) {
            return; // End of branch
        }

        for (var group : task.getPermissives()) {
            for (var perm : group) {
                perm.getLastResult().setExpirationTime(millis);
            }
        }

        // Recursively collect descriptions from success and failure branches if not a leaf
        if (!task.isLeaf()) {
            updateResultExpirationTime((BranchTask)task.successTask(), millis);
            updateResultExpirationTime((BranchTask)task.failureTask(), millis);
        }
    }
}
