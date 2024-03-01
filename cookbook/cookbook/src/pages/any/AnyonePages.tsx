import {Route, Routes} from "react-router-dom";
import Home from "./Home";
import Authentication from "./Authentication";
import React, {useEffect} from "react";
import {CookBookService} from "../../services/CookBookService";
import {cookServerInstance} from "../../App";
import {toast} from "react-toastify";

interface AnyonePagesProps {
    setUser: (user: any) => void;
}

function AnyonePages({setUser}: AnyonePagesProps) {

    const cookbookService = new CookBookService();

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (token) {
            cookServerInstance.defaults.headers.common['Authorization'] = token;

            cookbookService.getUser()
                .then((response) => {
                    setUser(response);
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log(error);
                });
        }

    }, []);


    return (
        <div className={"min-h-screen mx-auto bg-cook-orange"}>
            <Routes>
                <Route path="" element={<Home/>}/>
                <Route path="authentication/*" element={<Authentication setUser={setUser}/>}/>
            </Routes>
        </div>
    );
}

export default AnyonePages;