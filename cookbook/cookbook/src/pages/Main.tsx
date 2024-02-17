import {Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";
import Home from "./Home";
import React, {useState} from "react";
import {IUser} from "../assets/models/Authentication";
import Header from "../components/main/Header";
import Footer from "../components/main/Footer";
import Authentication from "./Authentication";
import Landing from "./Landing";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <>
            <Header user={user} setUser={setUser}/>
            <main className="App-main min-vh-100 mx-auto text-cook">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/home" element={<Home/>}/>
                    <Route path="/authentication/*" element={<Authentication setUser={setUser}/>}/>
                    <Route path="/landing" element={<Landing/>}/>

                    <Route path="*" element={<PageNotFound/>}/>
                </Routes>
            </main>
            <Footer/>
        </>
    );
}

export default Main;