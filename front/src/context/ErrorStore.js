import {makeAutoObservable} from "mobx";

export default class ErrorStore{

    constructor() {
        this.message = ""
        makeAutoObservable(this)
    }

    setMessageWithTimeout(message){
        this.message = message
        setTimeout(()=>{
            this.message = ""
        }, 5000)
    }
}