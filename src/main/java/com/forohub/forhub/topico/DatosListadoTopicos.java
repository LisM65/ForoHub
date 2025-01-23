package com.forohub.forhub.topico;

public record DatosListadoTopicos(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso) {
    public DatosListadoTopicos(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso());
    }
}