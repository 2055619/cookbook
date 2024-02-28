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

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <>
            <Header user={user} setUser={setUser}/>
            <main className="App-main min-vh-100 mx-auto text-cook bg-second-cook">
                <div className="row">
                    <LeftAside />
                    <div className="col">
                        <Routes>
                            <Route path="/" element={<Home/>}/>
                            <Route path="/home" element={<Home/>}/>
                            <Route path="/authentication/*" element={<Authentication setUser={setUser}/>}/>
                            <Route path="/landing" element={<Landing/>}/>
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