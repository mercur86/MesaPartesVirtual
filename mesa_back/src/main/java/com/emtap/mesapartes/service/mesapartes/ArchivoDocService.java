package com.emtap.mesapartes.service.mesapartes;

import com.emtap.mesapartes.entity.mesapartes.ArchivoDocId;
import com.emtap.mesapartes.entity.mesapartes.ArchivosDoc;
import com.emtap.mesapartes.repository.mesapartes.ArchivoDocRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ArchivoDocService {

    @Autowired
    private ArchivoDocRepository archivoDocRepository;

    @Transactional
    public String guardarArchivo(
            String nuAnn,
            String nuEmi,
            MultipartFile file) {

        try {
            ArchivoDocId id = new ArchivoDocId(nuAnn, nuEmi);

            // 1. Si existe lo carga, si no crea uno nuevo
            ArchivosDoc archivo = archivoDocRepository
                    .findById(id)
                    .orElseGet(() -> {
                        ArchivosDoc nuevo = new ArchivosDoc();
                        nuevo.setId(id);
                        nuevo.setEsFirma("0"); // default según tu tabla
                        return nuevo;
                    });

            // 2. Asignar archivo principal
            archivo.setBlDoc(file.getBytes());
            archivo.setRutaOrigen(file.getOriginalFilename());

            // FEULA formato YYYYMMDD
            archivo.setFeUla(LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMdd")
            ));

            // 3. Si es .DOCX => también actualizar w_bl_doc y w_de_ruta_origen
            String nombre = file.getOriginalFilename();
            if (nombre != null && nombre.toUpperCase().endsWith(".DOCX")) {
                archivo.setWBlDoc(file.getBytes());
                archivo.setWRutaOrigen(nombre);
            }

            // 4. Guardar o actualizar (JPA decide)
            archivoDocRepository.save(archivo);

            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return "NO OK";
        }
    }
}
