import React, {useState} from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faStar} from '@fortawesome/free-solid-svg-icons';

interface StarDropdownProps {
    selectedStars: number;
    setSelectedStars: (stars: number) => void;
}
function StarDropdown({selectedStars, setSelectedStars}: StarDropdownProps) {
    const [isOpen, setIsOpen] = useState(false);

    const stars = [1, 2, 3, 4, 5];

    function handleStarClick(star: number) {
        setSelectedStars(star);
        setIsOpen(false);
    }

    return (
        <div className="relative inline-block text-left">
            <div>
                <button type="button"
                        className="w-full inline-flex justify-center rounded-md border border-cook shadow-sm px-4 py-2 bg-white text-sm font-medium text-cook hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cook-light"
                        id="options-menu" aria-haspopup="true" aria-expanded="true" onClick={() => setIsOpen(!isOpen)}>
                    {
                        selectedStars === 0 ? (
                            <>
                                <span>All</span>
                                <div className="opacity-0 p-0 m-0">
                                    {Array(4).fill(0).map((_, i) => <FontAwesomeIcon key={i} icon={faStar}
                                                                                     className='text-cook'/>)}
                                </div>

                            </>
                        ) : (
                            Array(5).fill(0).map((_, i) =>
                                <FontAwesomeIcon key={i}
                                                 icon={faStar}
                                                 className={i < selectedStars ? 'text-cook-light' : 'text-cook'}/>)
                        )
                    }
                </button>
            </div>

            {isOpen && (
                <div
                    className="origin-top-right absolute right-0 mt-2 w-full rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5">
                    <div className="py-1" role="menu" aria-orientation="vertical" aria-labelledby="options-menu">
                        <button type="button"
                                className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900"
                                role="menuitem" onClick={() => handleStarClick(0)}>All
                        </button>
                        {stars.map((star, index) => (
                            <button key={index} type="button"
                                    className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900"
                                    role="menuitem" onClick={() => handleStarClick(star)}>
                                {Array(5).fill(0).map((_, i) => <FontAwesomeIcon key={i} icon={faStar}
                                                                                 className={i < star ? 'text-cook-light' : 'text-cook'}/>)}
                            </button>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}

export default StarDropdown;