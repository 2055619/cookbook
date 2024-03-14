import {Route, Routes} from "react-router-dom";
import Home from "./Home";
import Authentication from "./Authentication";
import React from "react";
import PageNotFound from "./PageNotFound";
import Policies from "./Policies";

interface AnyonePagesProps {
    setUser: (user: any) => void;
}

function AnyonePages({setUser}: AnyonePagesProps) {
    return (
        <div className={"min-h-screen bg-cook-orange text-center"}>
            <Routes>
                <Route path="" element={<Home/>}/>
                <Route path="authentication/*" element={<Authentication setUser={setUser}/>}/>
                <Route path="policies" element={<Policies/>}/>
                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </div>
    );
}

export default AnyonePages;