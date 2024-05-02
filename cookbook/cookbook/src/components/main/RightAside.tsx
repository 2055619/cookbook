import {IUser} from "../../assets/models/Authentication";
import React, {useState} from "react";
import { useTranslation } from "react-i18next";

interface IRightAside {
    user: IUser;
}
function RightAside({user}: IRightAside) {
    const {t} = useTranslation();

    // TODO: Find something to put here
    return (
        <div className="w-1/4 text-white text-end me-2 hidden md:block">

        </div>
    );
}

export default RightAside;