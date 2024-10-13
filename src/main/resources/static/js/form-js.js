import "https://unpkg.com/@bpmn-io/form-js@1.10.1/dist/form-editor.umd.js";

const
    schema='{"components": [{"key": "creditor","label": "Creditor","type": "textfield","validate": {"required": true} },  {"description": "An invoice number in the format: C-123.","key": "invoiceNumber","label": "Invoice Number","type": "textfield","validate": {"pattern": "^C-[0-9]+$"} },{"key": "approvedBy","label": "Approved By","type": "textfield"}, {"key": "submit","label": "Submit","type": "button"},{"action": "reset", "key": "reset","label": "Reset","type": "button"} ], "type": "default"}'

const data = {
    creditor: "John Doe Company",
    amount: 456,
    invoiceNumber: "C-123",
    approved: true,
    approvedBy: "John Doe"
};

const container = document.querySelector('#form');

const formEditor = FormEditor.createFormEditor({
    container: document.getElementById("container"),
    schema,
    data
});
