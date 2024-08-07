import React, { useState } from 'react';
import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Publication";

interface IInstructionCardProps {
    recipe: IRecipe;
}
function InstructionCard({recipe}: IInstructionCardProps) {
    const { t } = useTranslation();
    const [currentInstructionIndex, setCurrentInstructionIndex] = useState(0);

    const handlePrevious = () => {
        setCurrentInstructionIndex(currentInstructionIndex - 1);
    };

    const handleNext = () => {
        setCurrentInstructionIndex(currentInstructionIndex + 1);
    };

    return (
        <div className="card mt-2">
            <h2 className={"text-4xl"}>{t('instructions')}</h2>

            <div className={"text-2xl overflow-auto h-32 flex items-center justify-center"}
                 style={{maxHeight: '100px'}}>
                <span>{currentInstructionIndex + 1}. {recipe.instructions[currentInstructionIndex]}</span>
            </div>


            {/*TODO: Add Image / Gifs    */}

            <div className={" mt-2"}>
                {
                    currentInstructionIndex >= 1 ?
                        <button
                            className={"border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 py-1 px-3 mx-10"}
                            onClick={handlePrevious}>
                            {t('Previous')}
                        </button> : <button className={"invisible mx-16"}></button>
                }
                <span>instruction # {currentInstructionIndex + 1}</span>
                {
                    currentInstructionIndex < recipe.instructions.length - 1 ?
                        <button
                            className={"border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 py-1 px-3 mx-10"}
                            onClick={handleNext}>
                            {t('Next')}
                        </button> : <button className={"invisible mx-16"}></button>
                }
            </div>
        </div>
    );
}

export default InstructionCard;