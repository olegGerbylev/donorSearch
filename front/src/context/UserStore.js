import {makeAutoObservable} from "mobx";
import {useNavigate} from "react-router-dom";

export default class UserStore{
    _isAuth = false
    role = "guest"
    displayName = ""

    constructor() {
        makeAutoObservable(this)
    }

    logOut(){
        this._isAuth = false
        this.role = "guest"
        this.displayName = ""
        localStorage.removeItem("sessionId")
    }

    setIsAuth(bool){
        this._isAuth = bool
    }


    get isAuth(){
        return this._isAuth
    }
}