package com.jw.meetingscheduler.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

enum PublisherType{PUBLISHER, UNBAPTIZED_PUBLISHER, REGULAR_PIONEER, SPECIAL_PIONEER,
	ELDER, MINISTERIAL_SERVANT}

@Entity
@Table(name = "publishers")
public class Publisher {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "age")
	private int age;

	@Column(name = "first_name")
	@NotNull
	private String firstName;
	
	@Column(name = "last_name")
	@NotNull
	private String lastName;
	
	@Column(name = "email")
	@NotNull
	private String email;
	
	@Column(name = "notes")
	private String notes;
	
	@ManyToOne
	@JoinColumn(name="congregation_id")
	@JsonIgnore
	private Congregation congregation;
	
	@OneToMany(mappedBy = "publisher")
	private Set<Assignment> assignments;
	
	@OneToMany
	@JoinColumn(name="assistant_id")
	private Set<Assignment> assistantAssignments;
	
	@ElementCollection
	@JoinTable(name = "publisher_types", joinColumns = @JoinColumn(name = "publisher_id"))
	@Column(name = "publisher_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private Collection<PublisherType> publisherTypes;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Congregation getCongregation() {
		return congregation;
	}

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

	public Set<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}

	public Set<Assignment> getAssistantAssignments() {
		return assistantAssignments;
	}

	public void setAssistantAssignments(Set<Assignment> assistantAssignments) {
		this.assistantAssignments = assistantAssignments;
	}

	public Collection<PublisherType> getPublisherTypes() {
		return publisherTypes;
	}

	public void setPublisherTypes(Collection<PublisherType> publisherTypes) {
		this.publisherTypes = publisherTypes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
