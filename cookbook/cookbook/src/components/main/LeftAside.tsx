import React, {useEffect, useState} from "react";
import {IFilters} from "../../assets/models/Form";
import {IUser} from "../../assets/models/Authentication";
import FilterComponent from "../Utils/FilterComponent";

interface ILeftAside {
    setFilters: (filters: IFilters) => void;
    user: IUser | null;
}

function LeftAside({setFilters, user}: ILeftAside) {

    return (
        <div className="md:w-1/4 text-cook-light px-4 hidden md:block">
            {
                user ? <FilterComponent setFilters={setFilters} /> : ""
            }

        </div>
    );
}

// @ts-ignore
export default LeftAside;