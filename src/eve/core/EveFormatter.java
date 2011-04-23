package eve.core;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

	public class EveFormatter extends Formatter {
		public EveFormatter() {
			super();
		}
		
		public String format(LogRecord record) {
			StringBuilder sb = new StringBuilder();
			sb.append(record.getLevel()).append(':');
			sb.append(' ').append(record.getMessage()).append('\n');
			return sb.toString();
		}
	}