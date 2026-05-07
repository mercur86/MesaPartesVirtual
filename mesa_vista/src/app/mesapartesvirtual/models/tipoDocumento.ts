export interface TipoDocumento {
  cdocTipdoc: string;         // Código del tipo de documento (PK)
  cdocDesdoc?: string;        // Descripción del documento
  cdocIndbaj?: string;        // Indicador de baja (por ejemplo, 'S' o 'N')
  fdocFecbaj?: string;        // Fecha de baja (formato ISO: yyyy-MM-ddTHH:mm:ss)
  cdocGrupo?: string;         // Grupo de documento
  inNumeracion?: string;      // Indicador de numeración
  inTipoFirma?: string;       // Indicador de tipo de firma
  inDocSalida?: string;       // Indicador si es documento de salida
  inMultiple?: string;        // Indicador si permite múltiples documentos (por defecto '0')
}
