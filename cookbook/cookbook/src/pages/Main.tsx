import {Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";
import Home from "./Home";
import React, {useState} from "react";
import {IUser} from "../assets/models/Authentication";
import Header from "../components/main/Header";
import Footer from "../components/main/Footer";
import SignIn from "./SignIn";
import SignUp from "./SignUp";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <>
            <Header/>
            <main className="App-main min-vh-100 mx-auto">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/home" element={<Home/>}/>
                    <Route path="/signin" element={<SignIn/>}/>
                    <Route path="/signup" element={<SignUp/>}/>

                    <Route path="*" element={<PageNotFound/>}/>
                </Routes>
            </main>
            <Footer/>
        </>
    );
}

export default Main;