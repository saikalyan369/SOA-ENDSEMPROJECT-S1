package com.school.result.service;

import com.school.result.entity.Result;
import com.school.result.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Result createResult(Result result) {
        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Result not found with ID: " + id));
    }

    public List<Result> getResultsByStudentId(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }

    public Result updateResult(Long id, Result result) {

        Result existingResult = getResultById(id);

        existingResult.setStudentId(result.getStudentId());
        existingResult.setSubject(result.getSubject());
        existingResult.setMarks(result.getMarks());

        return resultRepository.save(existingResult);
    }

    public void deleteResult(Long id) {

        Result existingResult = getResultById(id);

        resultRepository.delete(existingResult);
    }
}