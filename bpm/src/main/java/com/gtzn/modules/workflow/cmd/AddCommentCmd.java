package com.gtzn.modules.workflow.cmd;


import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;


/**
 * 改写AddCommentCmd
 */
public class AddCommentCmd implements Command<Comment> {

  protected String taskId;
  protected String processInstanceId;
  protected String type;
  protected String message;
  
  public AddCommentCmd(String taskId, String processInstanceId, String message) {
    this.taskId = taskId;
    this.processInstanceId = processInstanceId;
    this.message = message;
  }
  
  public AddCommentCmd(String taskId, String processInstanceId, String type, String message) {
    this.taskId = taskId;
    this.processInstanceId = processInstanceId;
    this.type = type;
    this.message = message;
  }
  
  public Comment execute(CommandContext commandContext) {
    
    // Validate task
   /* if (taskId != null) {
      TaskEntity task = commandContext.getTaskEntityManager().findTaskById(taskId);

      if (task == null) {
        throw new ActivitiObjectNotFoundException("Cannot find task with id " + taskId, Task.class);
      }

      if (task.isSuspended()) {
        throw new ActivitiException(getSuspendedTaskException());
      }
    }*/
    
    if (processInstanceId != null) {
      ExecutionEntity execution = commandContext.getExecutionEntityManager().findExecutionById(processInstanceId);

      if (execution == null) {
        throw new ActivitiObjectNotFoundException("execution " + processInstanceId + " doesn't exist", Execution.class);
      }

      if (execution.isSuspended()) {
        throw new ActivitiException(getSuspendedExceptionMessage());
      }
    }
    
    String userId = Authentication.getAuthenticatedUserId();
    CommentEntity comment = new CommentEntity();
    comment.setUserId(userId);
    comment.setType( (type == null)? CommentEntity.TYPE_COMMENT : type );
    comment.setTime(commandContext.getProcessEngineConfiguration().getClock().getCurrentTime());
    comment.setTaskId(taskId);
    comment.setProcessInstanceId(processInstanceId);
    comment.setAction(Event.ACTION_ADD_COMMENT);
    
    String eventMessage = message.replaceAll("\\s+", " ");
    if (eventMessage.length()>163) {
      eventMessage = eventMessage.substring(0, 160)+"...";
    }
    comment.setMessage(eventMessage);
    
    comment.setFullMessage(message);
    
    commandContext
      .getCommentEntityManager()
      .insert(comment);
    
    return comment;
  }
  
  protected String getSuspendedTaskException() {
    return "Cannot add a comment to a suspended task";
  }
  
  protected String getSuspendedExceptionMessage() {
    return "Cannot add a comment to a suspended execution";
  }
}
