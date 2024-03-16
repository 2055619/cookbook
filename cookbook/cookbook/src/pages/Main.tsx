import {Route, Routes} from "react-router-dom";
import PageNotFound from "./any/PageNotFound";
import React, {useState} from "react";
import {IUser} from "../assets/models/Authentication";
import Header from "../components/main/Header";
import Footer from "../components/main/Footer";
import LeftAside from "../components/main/LeftAside";
import RightAside from "../components/main/RightAside";
import UserPages from "./user/UserPages";
import AnyonePages from "./any/AnyonePages";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <>
            <Header user={user} setUser={setUser}/>
            <main className="min-h-screen bg-cook text-cook font-semibold">
                <div className="flex">
                    <LeftAside />
                    <div className="w-full md:w-full">
                        <Routes>
                            <Route path="/*" element={<AnyonePages setUser={setUser}/>}/>
                            <Route path="/u/*" element={<UserPages setUser={setUser} user={user}/>}/>
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