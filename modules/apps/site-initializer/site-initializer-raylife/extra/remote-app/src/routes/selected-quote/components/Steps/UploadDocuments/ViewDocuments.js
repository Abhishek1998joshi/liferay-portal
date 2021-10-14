import ClayIcon from '@clayui/icon';

const ViewBody = ({
	file,
	onRemoveFile,
	showCloseButton = true,
	showName = true,
}) => (
	<>
		{showName && <span className="ellipsis">{file.name}</span>}

		{showCloseButton && (
			<div className="close-icon" onClick={() => onRemoveFile(file)}>
				<ClayIcon symbol="times" />
			</div>
		)}
	</>
);

const ViewDocuments = ({
	file,
	onRemoveFile,
	showCloseButton = true,
	showName = true,
	type = 'image',
}) => (
	<div className="view-file-document">
		<div className="div-document" title={file.name}>
			<div className="content">
				{type === 'image' ? (
					<div className="image">
						<img alt={file.name} src={file.fileURL} />
					</div>
				) : (
					<ClayIcon className={file.icon} symbol={file.icon} />
				)}
			</div>

			<ViewBody
				file={file}
				onRemoveFile={onRemoveFile}
				showCloseButton={showCloseButton}
				showName={showName}
			/>
		</div>
	</div>
);

export default ViewDocuments;
