package br.com.ferrogestao.domain.enums;

public enum TrafficStatus {
    GREEN("No alvo"), YELLOW("Atenção"), RED("Crítico"), BLUE("Superado"), GRAY("Sem apontamento");

    private final String label;
    TrafficStatus(String label) { this.label = label; }
    public String getLabel() { return label; }
}