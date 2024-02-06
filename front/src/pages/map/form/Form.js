import React, {useEffect, useState} from 'react';
import Input from "../../../components/input/Input";
import {convertErrors} from "../../../helper/convertError";
import classes from'./form.module.scss'

const Form = () => {
    const [formState, setFormState] = useState({
        values: {},
        errors: {},
    });
    const [errors, setErrors] = useState([]);
    const [image, setImage] = useState(null);
    const [byteCode, setByteCode] = useState(null);

    useEffect(() => {
        if (errors) {
            setFormState({
                ...formState,
                ...convertErrors(errors),
            });
        }
    }, [errors]);

    const handleChange = (event) => {
        // event.persist();
        setFormState(({
            ...formState,
            values: {
                ...formState.values,
                [event.target.name]: event.target.type === 'checkbox' ? event.target.checked : event.target.value,
            },
        }));
        console.log(formState)
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                const imageData = reader.result;
                setImage(imageData);
                encodeImageToByteCode(imageData);
            };
            reader.readAsDataURL(file);
        }
    };

    const encodeImageToByteCode = (imageData) => {
        // Пример кодирования в байт-код, может потребоваться использовать другие методы
        const byteCodeArray = new Uint8Array(
            atob(imageData.split(',')[1]).split('').map((char) => char.charCodeAt(0))
        );
        setFormState(({
            ...formState,
            values: {
                ...formState.values,
                photo: byteCodeArray,
            },
        }));
    };

    return (
        <div className={classes.form}>
            <div className={classes.photo}>
                {image && <img src={image} alt="Uploaded"/>}
            </div>
            <Input name={"name"} label={"название здания"} onChangeCallback={handleChange}/>
            <Input name={"address"} label={"адрес"} onChangeCallback={handleChange}/>
            <Input name={"phone"} label={"телефон"} onChangeCallback={handleChange}/>
            <Input name={"info"} label={"информация о посещении"} onChangeCallback={handleChange}/>
            <Input name={"important"} label={"важно"} onChangeCallback={handleChange} />
            <Input name={"time"} label={"Введите время работы в формате HH:MM-HH:MM"} onChangeCallback={handleChange} />
            <label className={classes.customFileUpload}>
                <input type="file" onChange={handleImageChange} />
                <span>Добавить картинку</span>
            </label>
            <div className={classes.submitButton}>Сохранить</div>
        </div>
    );
};

export default Form;