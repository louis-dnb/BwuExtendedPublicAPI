package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.script.treescript.interfaces.ITreeTask;
import net.botwithus.api.game.script.treescript.permissive.Result;
import net.botwithus.rs3.script.Script;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Represents a task in a tree structure. This is an abstract base class for
 * all types of tasks.
 */
public abstract class TreeTask implements ITreeTask {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    protected Result latestValidate = new Result(false);
    private String definedIn = "";

    private Script script;
    private Callable<String> desc = () -> "";

    public TreeTask(Script script) {
        this.script = script;
    }

    public TreeTask(Script script, String desc) {
        this.script = script;
        this.desc = () -> desc;
    }

    public TreeTask(Script script, String desc, String definedIn) {
        this.script = script;
        this.desc = () -> desc;
        this.definedIn = definedIn;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void execute();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask successTask();

    /** {@inheritDoc} */
    @Override
    public abstract boolean validate();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask failureTask();

    /** {@inheritDoc} */

    @Override
    public abstract boolean isLeaf();

    public String getDesc() {
        if (desc != null) {
            try {
                return desc.call();
            } catch (Exception e){
                log.atSevere().withCause(e).log("Failed to determine the result of the Callable<successTaskC>");
            }
        }
        return "";
    }

    public void setDesc(String desc) {
        this.desc = () -> desc;
    }

    public Script getScript() {
        return script;
    }

    public String getDefinedIn() {
        return definedIn;
    }

    /**
     * Traverses the given tree of tasks, executing the appropriate task
     * based on the result of validation.
     * @param treeTask The root task of the tree to traverse.
     */
    public static void traverse(Script script, TreeTask treeTask) {
        var validate = treeTask.validate();
        treeTask.setLatestValidate(validate);
        if (!treeTask.isLeaf()) {
            var msg = "[TreeTaskBranch] " + (treeTask.getDesc().length() > 0 ? treeTask.getDesc() : treeTask.getClass().getSimpleName()) + ": " + validate;
            log.atInfo().log(msg);
            script.println(msg);
            traverse(script, validate ? treeTask.successTask() : treeTask.failureTask());
        } else {
            treeTask.execute();
        }
    }

    public Result getLatestValidate() {
        return latestValidate;
    }

    public void setLatestValidate(boolean lastLoopValidate) {
        this.latestValidate = new Result(lastLoopValidate);
    }

    /**
     * Collects descriptions from all tasks in the tree starting from the specified root task.
     *
     * @param rootTask The root task of the tree to start collecting descriptions from.
     * @return A list of descriptions for all tasks in the tree.
     */
    public static List<String> getAllTaskDescriptions(TreeTask rootTask) {
        List<String> descriptions = new ArrayList<>();
        collectDescriptions(rootTask, descriptions);
        return descriptions;
    }

    /**
     * Helper method to recursively collect descriptions of tasks into the provided list.
     *
     * @param task The current task to collect the description from.
     * @param descriptions The list where descriptions are being collected.
     */
    private static void collectDescriptions(TreeTask task, List<String> descriptions) {
        if (task == null) {
            return; // End of branch
        }

        // Add the current task's description
        descriptions.add(task.getDesc());

        // Recursively collect descriptions from success and failure branches if not a leaf
        if (!task.isLeaf()) {
            collectDescriptions(task.successTask(), descriptions);
            collectDescriptions(task.failureTask(), descriptions);
        }
    }

    /**
     * Adds a flag to the description of all tasks in the tree starting from the specified root task.
     *
     * @param flag The flag to add to the description of all tasks in the tree.
     */
    public <T> void updateDescriptionFlag(Class<T> classType, String flag) {
        updateDescriptionFlag(classType, this, flag);
    }

    /**
     * Adds a flag to the description of all tasks in the tree starting from the specified root task.
     *
     * @param task The root task of the tree to start adding the flag to.
     * @param flag The flag to add to the description of all tasks in the tree.
     */
    public static <T> TreeTask updateDescriptionFlag(Class<T> type, TreeTask task, String flag) {
        if (task == null) {
            return null; // End of branch
        }

        // Add the current task's description
        if (task.getDesc() != null && !task.getDesc().contains(flag)
                && task.getDefinedIn() != null && task.getDefinedIn().contains(type.getSimpleName())) {
            var desc = task.getDesc();
            if (desc.contains("[")) {
                desc = desc.substring(0, desc.indexOf("[") - 1);
            }
            task.setDesc(desc + " " + flag);
        }

        // Recursively collect descriptions from success and failure branches if not a leaf
        if (!task.isLeaf()) {
            updateDescriptionFlag(type, task.successTask(), flag);
            updateDescriptionFlag(type, task.failureTask(), flag);
        }
        return task;
    }

    /**
     * Returns the task that matches the specified description.
     *
     * @param description The description to match.
     */
    public TreeTask findTaskByDescription(String description) {
        if (description == null || description.isEmpty()) {
            return null;
        }

        if (this.getDesc().contains(description)) {
            return this;
        }
        var checkSuccess = this.successTask().findTaskByDescription(description);
        if (checkSuccess != null) {
            return checkSuccess;
        }
        var checkFailure = this.failureTask().findTaskByDescription(description);
        if (checkFailure != null) {
            return checkFailure;
        }
        return null;
    }

}
