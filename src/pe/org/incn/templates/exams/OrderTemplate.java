package pe.org.incn.templates.exams;

import jpos.JposException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;

public class OrderTemplate extends JSONPrintable {

    public OrderTemplate(JSONObject json, EpsonPrintable printer) {
        super(json, printer);
    }

    @Override
    protected void canvas() throws JposException {
        this.printDocumentHeader();

        this.breakLine();

        this.separator();

        this.replicate('-');

        this.printBody();

        this.printObservations();
    }

    private void printDocumentHeader() throws JposException {
        JSONObject header = this.object.getJSONObjec("header");

        writer.centerBoldWords("Orden de Exámenes y Procedimientos Auxiliares");

        writer.wrapper(
                w -> w.groupOneLine("N° Orden", header.json("number")),
                w -> w.groupOneLine("N° Atención", header.json("attention_id")),

                w -> w.groupOneLine("Emisión", header.json("emission_date")),
                w -> w.groupOneLine("Pre-factura", header.json("account_id"))
        );
    }

    private void printBody() throws JposException {

        JSONObject patient = this.object.getJSONObjec("patient");

        writer.groupWords("Paciente", patient.json("name"));

        writer.wrapper(
                w -> w.groupMultiLine(patient.json("identity_document_type"), patient.json("identity_document")),

                w -> w.groupOneLine("Edad", patient.json("age")),
                w -> w.groupOneLine("Origen", patient.json("origin"))

        );

        if (patient.is("is_sis")) {
            writer.wrapper(
                    w -> w.groupOneLine("Categoría", patient.json("category")),
                    w -> w.groupOneLine("N° Fua", patient.json("fua"))

            );
        }
        this.breakLine();

        writer.wrapper(
                w -> w.groupMultiLine("Profesional Tratante", json("medico_name"))
        );

        this.printDetail();
    }

    private void printDetail() throws JposException {
        OrderExamDetail table = new OrderExamDetail(this);
        JSONArray items = this.object.getJSONArray("detail");
        table.draw(items);
    }

    private void printObservations() throws JposException {
        if (this.has("observation")) {
            this.breakLine();
            writer.writeBoldLine("Observación:");
            writer.writeBoldLine(json("observation"));
        }
    }
}
