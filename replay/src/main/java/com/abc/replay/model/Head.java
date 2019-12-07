
package com.abc.replay.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "event_id",
    "event_name",
    "total_size",
    "chunk_size",
    "chunk_num",
    "source_ip",
    "start_ts",
    "destination",
    "upload_length",
    "upload_defer_length",
    "upload_metadata",
    "hdfsLocation",
    "verId"
})
public class Head {
	
    @JsonProperty("id")
    private String id;
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("total_size")
    private String totalSize;
    @JsonProperty("chunk_size")
    private String chunkSize;
    @JsonProperty("chunk_num")
    private String chunkNum;
    @JsonProperty("source_ip")
    private String sourceIp;
    @JsonProperty("start_ts")
    private String startTs;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("upload_length")
    private String uploadLength;
    @JsonProperty("upload_defer_length")
    private String uploadDeferLength;
    @JsonProperty("upload_metadata")
    private String uploadMetadata;
    @JsonProperty("hdfsLocation")
    private String hdfsLocation;
    @JsonProperty("verId")
    private String verId;
    @JsonProperty("destination_path")
    private String destinationPath;
//    @JsonProperty("multiparts")
//    private String[] multiparts;
    

//    public String[] getMultiparts() {
//		return multiparts;
//	}
//
//	public void setMultiparts(String[] multiparts) {
//		this.multiparts = multiparts;
//	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("event_id")
    public String getEventId() {
        return eventId;
    }

    @JsonProperty("event_id")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @JsonProperty("event_name")
    public String getEventName() {
        return eventName;
    }

    @JsonProperty("event_name")
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @JsonProperty("total_size")
    public String getTotalSize() {
        return totalSize;
    }

    @JsonProperty("total_size")
    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    @JsonProperty("chunk_size")
    public String getChunkSize() {
        return chunkSize;
    }

    @JsonProperty("chunk_size")
    public void setChunkSize(String chunkSize) {
        this.chunkSize = chunkSize;
    }

    @JsonProperty("chunk_num")
    public String getChunkNum() {
        return chunkNum;
    }

    @JsonProperty("chunk_num")
    public void setChunkNum(String chunkNum) {
        this.chunkNum = chunkNum;
    }

    @JsonProperty("source_ip")
    public String getSourceIp() {
        return sourceIp;
    }

    @JsonProperty("source_ip")
    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    @JsonProperty("start_ts")
    public String getStartTs() {
        return startTs;
    }

    @JsonProperty("start_ts")
    public void setStartTs(String startTs) {
        this.startTs = startTs;
    }

    @JsonProperty("destination")
    public String getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @JsonProperty("upload_length")
    public String getUploadLength() {
        return uploadLength;
    }

    @JsonProperty("upload_length")
    public void setUploadLength(String uploadLength) {
        this.uploadLength = uploadLength;
    }

    @JsonProperty("upload_defer_length")
    public String getUploadDeferLength() {
        return uploadDeferLength;
    }

    @JsonProperty("upload_defer_length")
    public void setUploadDeferLength(String uploadDeferLength) {
        this.uploadDeferLength = uploadDeferLength;
    }

    @JsonProperty("upload_metadata")
    public String getUploadMetadata() {
        return uploadMetadata;
    }

    @JsonProperty("upload_metadata")
    public void setUploadMetadata(String uploadMetadata) {
        this.uploadMetadata = uploadMetadata;
    }

    
    
    @JsonProperty("hdfsLocation")
    public String getHdfsLocation() {
        return hdfsLocation;
    }

    @JsonProperty("hdfsLocation")
    public void setHdfsLocation(String hdfsLocation) {
        this.hdfsLocation = hdfsLocation;
    }

    @JsonProperty("verId")
    public String getVerId() {
        return verId;
    }

    @JsonProperty("verId")
    public void setVerid(String verId) {
        this.verId = verId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
