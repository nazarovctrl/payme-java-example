package com.example.paymejava.controller;

import com.example.paymejava.dto.request.*;
import com.example.paymejava.dto.result.Error;
import com.example.paymejava.dto.result.Result;
import com.example.paymejava.enums.Method;
import com.example.paymejava.dto.result.PerformTransaction;
import com.example.paymejava.service.IMerchantService;
import com.example.paymejava.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MerchantController {
    private final IMerchantService merchantService;
    private final AuthUtil authUtil;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @PostMapping
    public ResponseEntity<?> handle(HttpServletRequest request) {
        if (authUtil.isUnauthorized(request.getHeader("Authorization"))) {
            return ResponseEntity.ok(gson.toJson(new Result(new Error(-32504, "Unauthorized", "authorization"))));
        }

        try {
            String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            var map = new ObjectMapper().readValue(json, Map.class);
            String method = map.get("method").toString();
            String params = map.get("params").toString();
            if (method == null || method.isBlank() || params == null || params.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(control(method, params));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public String control(String method, String params) {
        Result result = new Result();
        switch (Method.valueOf(method)) {
            case CheckPerformTransaction ->
                    result.setResult(merchantService.checkPerformTransaction(gson.fromJson(params, CheckPerformTransaction.class)));
            case CreateTransaction ->
                    result.setResult(merchantService.createTransaction(gson.fromJson(params, CreateTransaction.class)));
            case PerformTransaction ->
                    result.setResult(merchantService.performTransaction(gson.fromJson(params, PerformTransaction.class)));
            case CheckTransaction ->
                    result.setResult(merchantService.checkTransaction(gson.fromJson(params, CheckTransaction.class)));
            case CancelTransaction ->
                    result.setResult(merchantService.cancelTransaction(gson.fromJson(params, CancelTransaction.class)));
            case GetStatement ->
                    result.setResult(merchantService.getStatement(gson.fromJson(params, GetStatement.class)));
        }
        return gson.toJson(result);
    }
}
