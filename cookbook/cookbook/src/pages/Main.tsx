import {Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";
import Home from "./Home";

function Main() {

    return (
        <main className="App-main min-vh-100 bg-light mx-auto">
            <Routes>
                <Route path="/" element={<Home/>}/>

                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </main>
    );
}

export default Main;