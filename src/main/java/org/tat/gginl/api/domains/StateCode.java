package org.tat.gginl.api.domains;

	import java.io.Serializable;

	import javax.persistence.Embedded;
	import javax.persistence.Entity;
	import javax.persistence.EntityListeners;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.NamedQueries;
	import javax.persistence.NamedQuery;
	import javax.persistence.Table;
	import javax.persistence.TableGenerator;
	import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;

import lombok.Data;


	@Entity
	@Data
	public class StateCode implements Serializable {
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "STATECODE_GEN")
		private String id;
		private String name;
		private String codeNo;

		@Version
		private int version;

		@Embedded
		private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

		public StateCode() {

		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCodeNo() {
			return codeNo;
		}

		public void setCodeNo(String codeNo) {
			this.codeNo = codeNo;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
			return commonCreateAndUpateMarks;
		}

		public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
			this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((codeNo == null) ? 0 : codeNo.hashCode());
			result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + version;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StateCode other = (StateCode) obj;
			if (codeNo == null) {
				if (other.codeNo != null)
					return false;
			} else if (!codeNo.equals(other.codeNo))
				return false;
			if (commonCreateAndUpateMarks == null) {
				if (other.commonCreateAndUpateMarks != null)
					return false;
			} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (version != other.version)
				return false;
			return true;
		}

	


}