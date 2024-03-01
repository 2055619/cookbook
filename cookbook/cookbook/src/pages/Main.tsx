import {Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";
import Home from "./Home";
import React, {useState} from "react";
import {IUser} from "../assets/models/Authentication";
import Header from "../components/main/Header";
import Footer from "../components/main/Footer";
import Authentication from "./Authentication";
import Landing from "./Landing";
import LeftAside from "../components/main/LeftAside";
import RightAside from "../components/main/RightAside";
import UserPages from "./UserPages";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <>
            <Header user={user} setUser={setUser}/>
            <main className="min-h-screen mx-auto bg-cook">
                <div className="flex">
                    <LeftAside />
                    <div className="w-3/4">
                        <Routes>
                            <Route path="/" element={<Home/>}/>
                            <Route path="/home" element={<Home/>}/>
                            <Route path="/authentication/*" element={<Authentication setUser={setUser}/>}/>
                            <Route path="/u/*" element={<UserPages user={user}/>}/>
                            <Route path="*" element={<PageNotFound/>}/>
                        </Routes>
                    </div>
                    <RightAside />
                </div>
            </main>
            <Footer/>
        </>
    );
}

export default Main;