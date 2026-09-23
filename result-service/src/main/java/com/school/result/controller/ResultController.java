package com.school.result.controller;

import com.school.result.entity.Result;
import com.school.result.service.ResultService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<Result> createResult(
            @RequestBody Result result) {

        return new ResponseEntity<>(
                resultService.createResult(result),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {

        return ResponseEntity.ok(
                resultService.getAllResults()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resultService.getResultById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getResultsByStudentId(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                resultService.getResultsByStudentId(studentId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(
            @PathVariable Long id,
            @RequestBody Result result) {

        return ResponseEntity.ok(
                resultService.updateResult(id, result)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResult(
            @PathVariable Long id) {

        resultService.deleteResult(id);

        return ResponseEntity.ok(
                "Result deleted successfully"
        );
    }
}