package com.forohub.forhub.controller;

import com.forohub.forhub.repository.RepositoryTopico;
import com.forohub.forhub.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class ControllerTopico {

    @Autowired
    private RepositoryTopico repositoryTopico;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = repositoryTopico.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = (new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso()));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listarTopicos(Pageable paginacion) {
        return ResponseEntity.ok(repositoryTopico.findAll(paginacion).map(DatosListadoTopicos::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(
            @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = repositoryTopico.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminartopico(@PathVariable Long id) {
        Topico Topico = repositoryTopico.getReferenceById((id));
        repositoryTopico.delete(new Topico());
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornarDatosRespuestaTopico(
            @PathVariable Long id) {
        Topico topico = repositoryTopico.getReferenceById((id));
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso());
        return ResponseEntity.ok(datosTopico);
    }
}