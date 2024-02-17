import React, {useContext, useState} from 'react';
import Button from "../../components/button/Button";
import axios from "axios";
import {Context} from "../../index";
import {useNavigate} from "react-router-dom";
import classes from './home.module.scss'
import Statistic from "./statistic/Statistic";
import AccountForm from "./accountForm/AccountForm";
import PetForm from "./petForm/PetForm";


const Home = () => {
    const navigate = useNavigate()
    const {user} = useContext(Context)
    const [isUserEdit, setIsUserEdit] = useState(false)

    const logout = ()=>{
        axios.post('http://localhost:8080/api/logout', {},
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("sessionId"),
                }
            }
        )
            .then(function (response) {
                user.logOut()
                navigate('/login')
            })
            .catch(function (e) {
                user.logOut()
                navigate('/login')
            });
    }

    return (
        <div className={classes.home}>
            <div className={classes.gridContainer}>
                <div className={classes.accountInfo} id={classes.infoField}>
                    <AccountForm isEdit={isUserEdit}/>
                    <div className={classes.accountAction}>
                        <div>
                            <Button text={isUserEdit ? "Сохранить" : "Редактировать"} onClick={()=>setIsUserEdit(!isUserEdit)} id={classes.margin}/>
                        </div>
                        <div>
                            <Button text={"Выйти из аккаунта"} onClick={logout} id={classes.margin}/>
                        </div>
                    </div>
                </div>
                <div className={classes.statistic} id={classes.infoField}>
                    <Statistic/>
                </div>
                <div className={classes.petInfo} id={classes.infoField}>
                    <PetForm/>
                </div>
            </div>
            <div className={classes.bottomIndent}></div>
        </div>
    );
};

export default Home;