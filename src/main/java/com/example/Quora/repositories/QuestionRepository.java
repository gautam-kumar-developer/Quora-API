package com.example.Quora.repositories;

import com.example.Quora.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q " +
            "LEFT JOIN q.topics t " +
            "WHERE (:text IS NULL OR q.title LIKE %:text%) " +
            "AND (:topics IS NULL OR t.name IN :topics) " +
            "GROUP BY q.id") // We need GROUP BY to avoid duplicate questions
    List<Question> findByTextAndTopics(
            @Param("text") String text,
            @Param("topics") Set<String> topics
    );
}