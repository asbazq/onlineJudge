import "./DescriptionPanel.css";

export default function DescriptionPanel(props) {
    const {
        data
    } = props;

    return (
        <div className="description-panel">
            <h2>{data?.title}</h2>
            <hr />
            <h4>Content</h4>
            <div>{data?.content}</div>
            {
                data?.example &&
                <>
                    <h4>Examples</h4>
                    <div>{data?.example}</div>
                </>
            }
            {
                data?.restriction &&
                <>
                    <h4>Restrictions</h4>
                    <div>{data?.restriction}</div>
                </>
            }
        </div>
    )
}