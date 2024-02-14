import {Route, Routes} from "react-router-dom";
import PageNotFound from "./PageNotFound";

function Main() {

    return (
        <main className='App-main min-vh-100 bg-light mx-auto'>
            <Routes>
                {/*<Route path="/" element={</>}/>*/}

                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </main>
    );
}

export default Main;