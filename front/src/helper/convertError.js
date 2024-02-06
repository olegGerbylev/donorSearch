export const convertErrors = (errors, idx) => {
    if (!errors || errors.length === 0) {
        return {
            errors: {},

        };
    }


    const fieldErrors = {};
    const touched = {};
    let mainError = null;
    errors.forEach(e => {

        if (e.args && e.args.field && e.args.field !== 'fields') {
            let idx2;
            if (idx) {
                idx = idx.toString();
                idx2 = [...e.args.field.matchAll(/fields\[(\d+)\].*/g)];
                idx2 = idx2[0][1];
            }
            let fieldName = idx ? e.args.field.slice(e.args.field.indexOf('.') + 1) : e.args.field;
            if (!fieldErrors[e.args.field] && (idx ? idx === idx2 : true)) {
                fieldErrors[fieldName] = e.message;
                touched[fieldName] = true;
            }
        } else if (mainError === null) {
            mainError = e.message || JSON.stringify(e);
        }
    });

    let ret = {
        isValid: false,
        error: mainError,
        errors: fieldErrors,
        touched: touched,
    };
    return ret;
}