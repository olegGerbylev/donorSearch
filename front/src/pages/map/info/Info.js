import React, {useContext} from 'react';
import classes from "./info.module.scss";
import Input from "../../../components/input/Input";
import {AuthContext} from "../../../context/authContext";

const Info = () => {
    const {currentUser} = useContext(AuthContext);

    return (
        <div className={""}>
            {/*<div className={classes.photo}>*/}
            {/*    {image && <img src={image} alt="Uploaded"/>}*/}
            {/*</div>*/}
            {/*<Input name={"name"} label={"название здания"} onChangeCallback={handleChange}/>*/}
            {/*<Input name={"address"} label={"адрес"} onChangeCallback={handleChange}/>*/}
            {/*<Input name={"phone"} label={"телефон"} onChangeCallback={handleChange}/>*/}
            {/*<Input name={"info"} label={"информация о посещении"} onChangeCallback={handleChange}/>*/}
            {/*<Input name={"important"} label={"важно"} onChangeCallback={handleChange} />*/}
            {/*<Input name={"time"} label={"Введите время работы в формате HH:MM-HH:MM"} onChangeCallback={handleChange} />*/}
            {/*<label className={classes.customFileUpload}>*/}
            {/*    <input type="file" onChange={handleImageChange} />*/}
            {/*    <span>Добавить картинку</span>*/}
            {/*</label>*/}
            {/*<div className={classes.submitButton}>Сохранить</div>*/}
            {currentUser && <>
                <div>Редактировать</div>
                <div>Удалить</div>
            </>}
        </div>
    );
};

export default Info;