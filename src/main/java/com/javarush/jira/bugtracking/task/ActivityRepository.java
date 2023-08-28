package com.javarush.jira.bugtracking.task;

import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
public interface ActivityRepository extends BaseRepository<Activity> {
    @Query("SELECT a FROM Activity a JOIN FETCH a.author WHERE a.taskId =:taskId ORDER BY a.updated DESC")
    List<Activity> findAllByTaskIdOrderByUpdatedDesc(long taskId);

    @Query("SELECT a FROM Activity a JOIN FETCH a.author WHERE a.taskId =:taskId AND a.comment IS NOT NULL ORDER BY a.updated DESC")
    List<Activity> findAllComments(long taskId);
    // doesn't work, wrong sql text
    // @Query("SELECT a FROM Activity a JOIN FETCH a.statusCode WHERE a.taskId =:taskId ORDER BY a.updated DESC")
    List<Activity> getByStatusCodeInAndTaskId(Set<String> statusCodes, long taskId);

    @Query(value = "select extract(epoch from (a2.updated - a1.updated)) \n" +
            "from activity a1, activity a2 \n" +
            "where a1.task_id = a2.task_id \n" +
            "and a1.task_id = ?1 \n" +
            "and a1.status_code = ?2 \n" +
            "and a2.status_code = ?3",
            nativeQuery = true)
    //it doesn't ready; think about how use it in ActivityService.java
    Optional<Long> getDurationInSeconds(Long taskId, String status1, String status2);
}
