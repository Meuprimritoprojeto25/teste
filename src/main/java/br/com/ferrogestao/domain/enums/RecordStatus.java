package br.com.ferrogestao.domain.enums;

public enum RecordStatus {
    DRAFT("Rascunho"), IN_REVIEW("Em revisão"), APPROVED("Aprovado"), ARCHIVED("Arquivado");
    private final String label;
    RecordStatus(String label) { this.label = label; }
    public String getLabel() { return label; }
}