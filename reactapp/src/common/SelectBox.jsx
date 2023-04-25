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
    const [open, setOpen] = useState(false);

    let value = controlledValue === undefined ? localValue : controlledValue;
    value = displayExpr === undefined ? value : value?.[placeHolder];

    const onClick = (e, idx) => {
        onChange({
            event: e,
            value: options[idx],
            previousValue: value,
        });
        setLocalValue(options[idx]);
    }

    const toggle = () => setOpen(cur => !cur);

    const reset = () => {
        onChange({
            event: null,
            value: undefined,
            previousValue: value,
        });
        setLocalValue(undefined);
    }

    return (
        <div className="select-box" onClick={toggle}>
            <div>
                {value === undefined ? placeHolder : value}
            </div>
            <ul className="select-box-content">
                {
                    options?.map((option, idx) => {
                        <li
                            key={idx}
                            onClick={(e) => onClick(e, idx)}
                        >
                            {displayExpr === undefined ? option : option?.[displayExpr]}
                        </li>
                    })
                }
            </ul>
        </div>
    )
}