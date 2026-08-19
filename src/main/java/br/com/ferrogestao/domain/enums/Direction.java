package br.com.ferrogestao.domain.enums;

public enum Direction {
    HIGHER_IS_BETTER("Maior é melhor"),
    LOWER_IS_BETTER("Menor é melhor"),
    RANGE("Faixa controlada");

    private final String label;
    Direction(String label) { this.label = label; }
    public String getLabel() { return label; }
}