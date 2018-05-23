package at.tortmayr.chillisp.api.actions;

public class ExportSVGAction extends Action {
	private String svg;

	public ExportSVGAction() {
		super(Action.Kind.EXPORT_SVG);
	}

	public ExportSVGAction(String svg) {
		this();
		this.svg = svg;
	}

	public String getSvg() {
		return svg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((svg == null) ? 0 : svg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportSVGAction other = (ExportSVGAction) obj;
		if (svg == null) {
			if (other.svg != null)
				return false;
		} else if (!svg.equals(other.svg))
			return false;
		return true;
	}

}
