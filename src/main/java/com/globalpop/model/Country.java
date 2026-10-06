package com.globalpop.model;

public record Country(
        String code,
        String name,
        String continent,
        String region,
        int population,
        String capital
) {
}