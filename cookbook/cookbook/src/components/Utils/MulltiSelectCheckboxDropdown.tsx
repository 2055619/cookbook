import React, { useState, useEffect, useRef } from 'react';
import {useTranslation} from "react-i18next";

interface MultiSelectCheckboxDropdownProps {
    options: string[];
    selectedOptions: string[];
    setSelectedOptions: (selectedOptions: string[]) => void;
    label: string;
}

function MultiSelectCheckboxDropdown({ options, selectedOptions, setSelectedOptions, label }: MultiSelectCheckboxDropdownProps) {
    const [isOpen, setIsOpen] = useState(false);
    const { t } = useTranslation();
    const dropdownRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        function handleClickOutside(event: any) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setIsOpen(false);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [dropdownRef]);

    function handleOptionClick(option: string) {
        if (selectedOptions.includes(option)) {
            setSelectedOptions(selectedOptions.filter(selectedOption => selectedOption !== option));
        } else {
            setSelectedOptions([...selectedOptions, option]);
        }
    }

    return (
        <div className="relative inline-block text-left w-full" ref={dropdownRef}>
            <div>
                <button type="button"
                        className="w-full z-20 inline-flex justify-center rounded-md border border-cook shadow-sm px-4 py-2 bg-white text-sm font-medium text-cook hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cook-light"
                        id="options-menu" aria-haspopup="true" aria-expanded="true" onClick={() => setIsOpen(!isOpen)}>
                    {label}
                </button>
            </div>

            {isOpen && (
                <div
                    className="z-10 origin-top-right absolute right-0 mt-2 w-full rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5">
                    <div className="py-1" role="menu" aria-orientation="vertical" aria-labelledby="options-menu">
                        {options.map((option, index) => (
                            <div className={"w-full clickable"} key={index} onClick={() => handleOptionClick(option)}>
                                <input className={"mx-2"} type="checkbox" checked={selectedOptions.includes(option)} />
                                <label className={"w-full"} >{t(option)}</label>
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}

export default MultiSelectCheckboxDropdown;