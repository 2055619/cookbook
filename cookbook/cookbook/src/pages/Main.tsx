import {Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";
import Home from "./Home";
import {useState} from "react";
import {IUser} from "../assets/models/Authentication";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);

    return (
        <main className="App-main min-vh-100 bg-cook mx-auto">
            <Routes>
                <Route path="/" element={<Home/>}/>

                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </main>
    );
}

export default Main;