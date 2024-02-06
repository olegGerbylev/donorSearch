import React, { useState } from 'react';
import './dropDown.scss'; // Подключение стилей
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
const DropDown = ({ options, onSelect }) => {
    const [selectedOption, setSelectedOption] = useState(null);
    const [isOpen, setIsOpen] = useState(false);

    const handleToggle = () => {
        setIsOpen(!isOpen);
    };

    const handleSelect = (option) => {
        setSelectedOption(option);
        setIsOpen(false);
        onSelect(option);
    };

    return (
        <div className={`dropdown ${isOpen ? 'open' : ''}`}>
            <div className="selected-option" onClick={handleToggle}>
                Ещё
                <ArrowDropUpIcon className={isOpen ? "open": "close"}/>
            </div>
            {isOpen && (
                <ul className="options">
                    {options.map((option) => (
                        <li key={option.value} onClick={() => handleSelect(option)}>
                            {option.label}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default DropDown;
