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
import {IFilters} from "../assets/models/Form";
import FilterComponent from "../components/Utils/FilterComponent";

function Main() {
    const [user, setUser] = useState<IUser | null>(null);
    const [filters, setFilters] = useState<IFilters>(
        {
            title: "",
            cookUsername: "",
            creationDate: "",
            difficulty: [],
            recipeType: [],
            ingredientName: "",
            dietType: [],
            prepTime: 0,
            cookTime: 0,
        } as IFilters
    );
    const [showFilters, setShowFilters] = useState(false);

    return (
        <>
            <Header user={user!} setUser={setUser} setShowFilters={setShowFilters} showFilters={showFilters}/>

            {showFilters && (
                    <FilterComponent setFilters={setFilters} />
            )}


            <main className="min-h-screen bg-cook text-cook font-semibold">
                <div className="flex">
                    <LeftAside user={user!} setFilters={setFilters}/>
                    <div className="w-full md:w-full">
                        <Routes>
                            <Route path="/*" element={<AnyonePages setUser={setUser}/>}/>
                            <Route path="/u/*" element={<UserPages setUser={setUser} user={user!} filters={filters}/>}/>
                            <Route path="*" element={<PageNotFound/>}/>
                        </Routes>
                    </div>
                    <RightAside user={user!}/>
                </div>
            </main>
            <Footer/>
        </>
    );
}

export default Main;