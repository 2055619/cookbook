import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import {IFilters} from "../../assets/models/Form";
import StarDropdown from "../reaction/StarDropdown";

interface ILeftAside {
    setFilters: (filters: IFilters) => void;
}

function LeftAside({setFilters}: ILeftAside) {
    const {t} = useTranslation();
    const [title, setTitle] = useState("");
    const [cookUsername, setCookUsername] = useState("");
    const [creationDate, setCreationDate] = useState("");
    const [stars, setStars] = useState(0);

    function handleSubmit(event: React.FormEvent) {
        event.preventDefault();
        console.log(title, cookUsername, creationDate, stars)
        // if ()
        setFilters({title: title, cookUsername: cookUsername, creationDate: creationDate, averageRating: stars});
    }

    function handleReset() {
        setFilters({title: "", cookUsername: "", creationDate: "", averageRating: 0});
        setTitle("");
        setCookUsername("");
        setCreationDate("");
        setStars(0);
    }

    return (
        <div className="md:w-1/4 text-cook-light px-4 hidden md:block">
            <h1 className={"text-4xl"}>{t('filter')}</h1>

            <form className={"sticky top-14"} onSubmit={handleSubmit}>
                <div>
                    <label className="block text-sm font-medium text-cook-light">{t('title')}</label>
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}
                           className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
                </div>

                <div>
                    <label className="block text-sm font-medium text-cook-light">{t('pages.auth.username')}</label>
                    <input type="text" value={cookUsername} onChange={(e) => setCookUsername(e.target.value)}
                           className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
                </div>

                <div>
                    <label className="block text-sm font-medium text-cook-light">{t('stars')}</label>
                    <StarDropdown selectedStars={stars} setSelectedStars={setStars}/>
                    {/*<input type="number" min="0" max="5" value={stars}*/}
                    {/*       onChange={(e) => setStars(Number(e.target.value))}*/}
                    {/*       className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>*/}
                </div>


                <div>
                    <label className="block text-sm font-medium text-cook-light">{t('creationDate')}</label>
                    <input type="date" value={creationDate} onChange={(e) => setCreationDate(e.target.value)}
                           className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
                </div>

                {/*TODO: ADD FILTERS*/}

                <div className="flex justify-between mt-3">
                    <button type={"button"} onClick={handleReset}
                            className="border border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-2 mx-2">
                        {t('input.reset')}
                    </button>

                    <button type="submit"
                            className="border border-cook-light text-cook-light hover:bg-cook-light hover:text-cook rounded transition ease-in duration-200 p-2 mx-2">
                        {t('input.apply')}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default LeftAside;