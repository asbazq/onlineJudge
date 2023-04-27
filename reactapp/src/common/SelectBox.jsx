import { useState } from "react";
import './SelectBox.css';

export default function SelectBox(props) {
    const {
        value: controlledValue,
        placeHolder = 'select...',
        onChange = () => { },
        displayExpr,
        options = [],
    } = props;

    const [localValue, setLocalValue] = useState();
    const [isOpen, setIsOpen] = useState(false);

    let value = controlledValue === undefined ? localValue : controlledValue;
    value = displayExpr === undefined ? value : value?.[displayExpr];

    const toggle = () => setIsOpen(cur => !cur);

    const onClick = (e, option) => {
        onChange({
            event: e,
            value: option,
            previousValue: value,
        });
        setLocalValue(option);
        setIsOpen(false);
    }

    const reset = () => {
        onChange({
            event: null,
            value: undefined,
            previousValue: value,
        });
        setLocalValue(undefined);
    }

    return (
        <div className="select-box">
            <div onClick={toggle}>
                {value === undefined ? placeHolder : value}
            </div>
            {
                isOpen &&
                <ul>
                    {
                        options.map((option, index) => {
                            return <li
                                key={index}
                                onClick={(e) => onClick(e, option)}
                            >
                                {displayExpr === undefined ? option : option[displayExpr]}
                            </li>
                        })
                    }
                </ul>
            }
        </div>
    )
}